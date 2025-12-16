import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';
import {lastValueFrom} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private readonly _http = inject(HttpClient)

  async uploadImage(file: File): Promise<{ publicId: string, url: string }> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', environment.PRESET_NAME);

    try {
      const res: any = await lastValueFrom(
        this._http.post(`${environment.CLOUDINARY_URL}image/upload`, formData)
      );

      return {
        publicId: res.public_id,
        url: res.secure_url
      };
    } catch (err) {
      console.error('Falha no upload para o Cloudinary:', err);
      throw err;
    }
  }
}
